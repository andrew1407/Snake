// Snake showcase: page controls around the game's own Java code (src/sample/*.java), compiled to
// snake-js/snake.js by TeaVM (teavm/, ./gradlew showcase) and drawn through the JavaFX shim (fx.js).

import * as fx from './fx.js';

const $ = (s) => document.querySelector(s);
const board = $('#board'), overlay = $('#overlay'), ovTitle = $('#ov-title'), ovText = $('#ov-text'), ovBtn = $('#ov-btn');
const statusEl = $('#status'), errEl = $('#err');

let state = 'ready';

const VIEWS = {
  ready: { title: 'Ready', text: 'Press an arrow key or WASD, or tap Start. On a phone, swipe to steer.', btn: 'Start' },
  loading: { title: 'Loading…', text: 'Starting the compiled Java game.', btn: 'Start', busy: true },
  paused: { title: 'Paused', text: 'Press Space or Enter, or tap Resume.', btn: 'Resume' },
  hidden: { title: 'Paused', text: 'The game paused while this tab was hidden. Press Space or Enter, or tap Resume.', btn: 'Resume' },
  over: { low: true, text: '', btn: 'Play again' },
  closed: { title: 'Game closed', text: 'Esc quits the game, as it closes the window on the desktop.', btn: 'Play again' },
};
const STATUS = { ready: 'Ready', loading: 'Loading', play: 'Playing', paused: 'Paused', hidden: 'Paused', over: 'Game over', closed: 'Closed' };

function setState(s) {
  state = s;
  const v = VIEWS[s];
  overlay.hidden = !v;
  if (v) {
    overlay.classList.toggle('low', !!v.low);
    ovTitle.hidden = !v.title;
    ovTitle.textContent = v.title || '';
    ovText.textContent = v.text;
    ovBtn.textContent = v.btn;
    ovBtn.disabled = !!v.busy;
  }
  statusEl.textContent = STATUS[s];
}

fx.on((type, detail) => {
  if (type === 'text' && detail === 'GAME OVER' && state === 'play') {
    setState('over');
  } else if (type === 'close') {
    setState('closed');
  }
});

async function startGame(firstKey) {
  if (state === 'loading') return;
  errEl.hidden = true;

  setState('loading');
  try {
    await fx.start();
    setState('play');
    if (firstKey) fx.press(firstKey);
  } catch (e) {
    console.error(e);
    errEl.textContent = `The game failed to start: ${e.message || e}`;
    errEl.hidden = false;
    setState('ready');
  }
}

function pauseGame(reason = 'paused') {
  if (state !== 'play') return;
  fx.pause();
  setState(reason);
}
function resumeGame() {
  if (state !== 'paused' && state !== 'hidden') return;
  fx.resume();
  setState('play');
}
const togglePause = () => (state === 'play' ? pauseGame() : resumeGame());
const primary = (key) => (state === 'paused' || state === 'hidden' ? resumeGame() : state === 'play' ? null : startGame(key));

// Direction input: the scene receives JavaFX KeyCode names, just like the desktop key filter.
const DIRS = ['UP', 'DOWN', 'LEFT', 'RIGHT', 'W', 'A', 'S', 'D'];
function steer(code) {
  if (state === 'play') fx.press(code);
  else if (state === 'ready') startGame(code);
  else if (state === 'paused' || state === 'hidden') { resumeGame(); fx.press(code); }
}

function keyCode(e) {
  const c = e.code;
  if (c.startsWith('Arrow')) return c.slice(5).toUpperCase();
  if (/^Key[A-Z]$/.test(c)) return c.slice(3);
  if (/^Digit\d$/.test(c)) return `DIGIT${c.slice(5)}`;
  return { Escape: 'ESCAPE', Enter: 'ENTER', Backspace: 'BACK_SPACE', ShiftLeft: 'SHIFT', ShiftRight: 'SHIFT' }[c] || 'UNDEFINED';
}

document.addEventListener('keydown', (e) => {
  if (e.ctrlKey || e.metaKey || e.altKey || e.key === 'Tab') return;
  const onButton = e.target.closest?.('button, a, input, select, textarea');
  const code = keyCode(e);
  if (e.code === 'Space' || code === 'P') {
    if (e.code === 'Space' && onButton) return; // let Space press the focused button
    e.preventDefault();
    if (!e.repeat) state === 'play' || state === 'paused' || state === 'hidden' ? togglePause() : primary();
    return;
  }
  if (code === 'ENTER') {
    if (onButton) return;
    e.preventDefault();
    primary();
    return;
  }
  if (DIRS.includes(code)) {
    e.preventDefault();
    steer(code);
    return;
  }
  if (state === 'play') fx.press(code); // any other key reaches the game too (Esc quits)
});

// Swipes anywhere on the screen: every 24 px of travel along the main axis is one turn.
let touch = null;
board.addEventListener('pointerdown', (e) => {
  if (e.pointerType === 'mouse' || e.target.closest('button')) return;
  touch = { id: e.pointerId, x: e.clientX, y: e.clientY };
});
board.addEventListener('pointermove', (e) => {
  if (!touch || e.pointerId !== touch.id) return;
  const dx = e.clientX - touch.x, dy = e.clientY - touch.y;
  if (Math.max(Math.abs(dx), Math.abs(dy)) < 24) return;
  steer(Math.abs(dx) > Math.abs(dy) ? (dx > 0 ? 'RIGHT' : 'LEFT') : (dy > 0 ? 'DOWN' : 'UP'));
  touch.x = e.clientX; touch.y = e.clientY;
});
const endTouch = (e) => { if (touch && e.pointerId === touch.id) touch = null; };
board.addEventListener('pointerup', endTouch);
board.addEventListener('pointercancel', endTouch);

ovBtn.addEventListener('click', () => primary());

document.addEventListener('visibilitychange', () => { if (document.hidden) pauseGame('hidden'); });

// An empty black board until the first game starts.
const ctx = fx.canvas.getContext('2d');
ctx.fillStyle = '#000';
ctx.fillRect(0, 0, fx.canvas.width, fx.canvas.height);
setState('ready');
