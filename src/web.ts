import { WebPlugin } from '@capacitor/core';

import type { GreatDayCameraPlugin } from './definitions';

export class GreatDayCameraWeb extends WebPlugin implements GreatDayCameraPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
