import { AppConfig } from "./app-config.interface";

// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
const BASE_SERVICES_URL = `https://uat.estesinternal.com`;
const BASE_DEPLOYMENT_URL = `https://estes-express-pre.estesinternal.com`;

export const environment: AppConfig = {
  serviceBaseUrl: `${BASE_SERVICES_URL}`,
  serviceApiUrl: `${BASE_SERVICES_URL}/api`,
  cmsUrl: `${BASE_DEPLOYMENT_URL}`,
  appBaseUrl: `${BASE_DEPLOYMENT_URL}/myestes`,
  authUrl: `${BASE_DEPLOYMENT_URL}/security/oauth`,
  production: true,
  cms: {
    contactUs: `${BASE_DEPLOYMENT_URL}/contact`,
    faq: `${BASE_DEPLOYMENT_URL}/resources/frequently-asked-questions`
  }
};

/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.