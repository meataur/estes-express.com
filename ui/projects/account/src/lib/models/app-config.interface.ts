export interface AppConfig {
  serviceBaseUrl: string;
  serviceApiUrl: string;
  cmsUrl: string;
  appBaseUrl: string;
  authUrl: string;
  production: boolean;
  cms: {
    contactUs: string;
    faq: string;
  };
}
