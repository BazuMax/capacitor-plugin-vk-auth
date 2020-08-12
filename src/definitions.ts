declare module '@capacitor/core' {
  interface PluginRegistry {
    VKAuth: VKAuthPlugin;
  }
}

export interface VKAuthPlugin {
  initWithId(options: { id: string }): Promise<{ value: string }>;
  auth(options: { scope: Array<string> }): Promise<{ value: any }>;
}
