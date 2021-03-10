declare module '@capacitor/core' {
  interface PluginRegistry {
    VKAuth: VKAuth;
  }
}

export interface VKAuth {
  initWithId(options: { id: string }): Promise<{ value: string }>;
  auth(options: { scope: string[] }): Promise<{ value: any }>;
}
