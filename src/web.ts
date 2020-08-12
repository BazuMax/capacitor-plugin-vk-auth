import { WebPlugin } from '@capacitor/core';
import { VKAuthPlugin } from './definitions';

export class VKAuthWeb extends WebPlugin implements VKAuthPlugin {
  constructor() {
    super({
      name: 'VKAuth',
      platforms: ['web'],
    });
  }

  async initWithId(options: { id: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return Promise.resolve({ value: options.id });
  }

  async auth(options: { scope: string[] }): Promise<{ value: any }> {
    console.log('ECHO', options);
    return Promise.resolve({ value: options });
  }
}

const VKAuth = new VKAuthWeb();

export { VKAuth };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(VKAuth);
