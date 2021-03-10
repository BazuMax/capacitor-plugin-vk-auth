import { registerPlugin } from '@capacitor/core';

import { VKAuth } from './definitions';

const VKAuth = registerPlugin<VKAuth>('VKAuth', {
  web: () => import('./web').then(m => new m.VKAuthWeb()),
});

export * from './definitions';
export { VKAuth };
