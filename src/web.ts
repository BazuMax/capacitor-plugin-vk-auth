import { WebPlugin } from '@capacitor/core';
import { VKAuthPlugin } from './definitions';

export class VKAuthWeb extends WebPlugin implements VKAuthPlugin {
  constructor() {
    super({
      name: 'VKAuth',
      platforms: ['web'],
    });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

const VKAuth = new VKAuthWeb();

export { VKAuth };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(VKAuth);
