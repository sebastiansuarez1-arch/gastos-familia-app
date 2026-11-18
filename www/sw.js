// Service worker mínimo: solo hace que Chrome permita instalar la página como app.
// NO guarda nada en caché a propósito: así siempre se ve la versión más nueva (V.0x).
self.addEventListener('install', () => self.skipWaiting());
self.addEventListener('activate', (e) => e.waitUntil(self.clients.claim()));
self.addEventListener('fetch', () => {}); // sin respondWith: el navegador va directo a la red
