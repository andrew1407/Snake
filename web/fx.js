// The page side of the JavaFX shim (web/teavm/src/main/java/javafx/...). The game's own Java
// (src/sample/*.java, compiled to snake-js/snake.js by TeaVM) calls window.fxHost to draw on the
// canvas, run its AnimationTimer and receive key presses. Exports let the page start, pause,
// restart and steer the game.

const canvas = document.getElementById('game');
const ctx = canvas.getContext('2d');

const timers = new Map(); // id -> frame callback (AnimationTimer.handle)
let keyHandlers = [], queued = [], nextId = 1, raf = 0;
let paused = false, pausedAt = 0, pausedTotal = 0, run = 0;
const listeners = new Set();
const emit = (type, detail) => listeners.forEach((f) => f(type, detail));

function frame() {
  raf = 0;
  if (paused || !timers.size) return;
  const now = performance.now() - pausedTotal; // the game's clock stands still while paused
  for (const cb of [...timers.values()]) cb(now);
  raf = requestAnimationFrame(frame);
}
const loop = () => { if (!raf && !paused && timers.size) raf = requestAnimationFrame(frame); };

window.fxHost = {
  title: (t) => emit('title', t),
  canvas: (w, h) => { canvas.width = w; canvas.height = h; },
  show: () => emit('show'),
  close: () => { stopAll(); emit('close'); }, // Esc: Stage.close()
  timer: (cb) => { const id = nextId++; timers.set(id, cb); loop(); return id; },
  stopTimer: (id) => timers.delete(id),
  onKey: (cb) => { keyHandlers.push(cb); queued.splice(0).forEach((k) => cb(k)); },
  gc: {
    fill: (css) => { ctx.fillStyle = css; },
    // JavaFX falls back to its system font when a font is missing; so does the browser here
    font: (name, size) => { ctx.font = `${/black|bold/i.test(name) ? '900 ' : ''}${size}px "${name}", system-ui, sans-serif`; },
    rect: (x, y, w, h) => ctx.fillRect(x, y, w, h),
    oval: (x, y, w, h) => { ctx.beginPath(); ctx.ellipse(x + w / 2, y + h / 2, w / 2, h / 2, 0, 0, 2 * Math.PI); ctx.fill(); },
    text: (t, x, y) => { ctx.fillText(t, x, y); emit('text', t); },
  },
};

function stopAll() {
  timers.clear(); keyHandlers = []; queued = [];
  if (raf) cancelAnimationFrame(raf);
  raf = 0;
}

export const on = (f) => listeners.add(f);

// Starts a fresh game: a new instance of the compiled module, so Main's static state starts over.
export async function start() {
  stopAll();
  paused = false; pausedTotal = 0;
  const mod = await import(`./snake-js/snake.js${run++ ? `?run=${run}` : ''}`);
  await new Promise((resolve, reject) => {
    try { mod.main([], (err) => (err ? reject(err) : resolve())); } catch (e) { reject(e); }
    setTimeout(resolve, 0);
  });
}

// Sends a JavaFX KeyCode name (W, UP, ESCAPE, ...) to the scene's key filter.
export function press(code) {
  if (keyHandlers.length) keyHandlers.forEach((cb) => cb(code));
  else queued.push(code);
}

export function pause() {
  if (paused) return;
  paused = true; pausedAt = performance.now();
  if (raf) cancelAnimationFrame(raf);
  raf = 0;
}
export function resume() {
  if (!paused) return;
  paused = false; pausedTotal += performance.now() - pausedAt;
  loop();
}
export const isPaused = () => paused;
export { canvas };
