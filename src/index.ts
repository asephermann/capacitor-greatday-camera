import { registerPlugin } from '@capacitor/core';

import type { GreatDayCameraPlugin } from './definitions';

const GreatDayCamera = registerPlugin<GreatDayCameraPlugin>('GreatDayCamera', {
  web: () => import('./web').then(m => new m.GreatDayCameraWeb()),
});

export * from './definitions';
export { GreatDayCamera };
