
export interface SimpleQuickLink {
  isApplication: boolean;
  icon: string;
  path: string;
  appName: string;
  description: string;
}

export const links = {
  SHIPTRACK: { uri: '', icon: 'fal fa-box-alt', path: '/shipment-tracking/', isApplication: true },
  DOCUMENTRETRIEVAL: { uri: '', icon: 'fal fa-envelope-open-text', path: '/document-retrieval/', isApplication: true },
  SDN015: { uri: '', icon: 'fal fa-shipping-timed', path: '/transit-time-calculator/', isApplication: true },
  EBG10O101: { uri: '', icon: 'fal fa-address-book', path: '/address-book/', isApplication: true },
  CLAIMIN: { uri: '', icon: 'fal fa-exclamation-circle', path: '/claims/', isApplication: true },
  CLAIMSFILE: { uri: '', icon: 'fal fa-file-exclamation', path: '/claims/', isApplication: true },
  PDN100: { uri: '', icon: 'fal fa-file-alt', path: '/image-viewing/', isApplication: true },
  RSG10O100: {
    uri: '',
    icon: 'fal fa-boxes',
    path: '/myestes/recent-shipments/',
    isApplication: false
  },
  SHIPMAN: {
    uri: '',
    icon: 'fal fa-clipboard',
    path: '/shipment-manifest/',
    isApplication: true
  },
  EDITPROF: {
    uri: '',
    icon: 'fal fa-user-edit',
    path: '/profile/',
    isApplication: false
  },
  QNN100: {
    uri: '',
    icon: 'fal fa-user',
    path: '/myestes/',
    isApplication: false
  },
  REQUESTINFO: {
    uri: '',
    icon: 'fal fa-info-circle',
    path: '/request-additional-info/',
    isApplication: false
  },
  EDN426: {
    uri: '',
    icon: 'fal fa-calculator',
    path: '/terminal-lookup/',
    isApplication: true
  },
  TMN100: {
    uri: '',
    icon: 'fal fa-calculator',
    path: '/terminal-lookup/',
    isApplication: true
  },
  PIL100: {
    uri: '',
    icon: 'fal fa-calculator',
    path: '/terminal-lookup/',
    isApplication: true
  },
  INVINQUIRY: {
    uri: '',
    icon: 'fal fa-file-invoice',
    path: '/invoice-inquiry/',
    isApplication: true
  },
  PKN211: {
    uri: '',
    icon: 'fal fa-truck',
    path: '/pickup-request/',
    isApplication: true
  },
  PKN200: {
    uri: '',
    icon: 'fal fa-truck',
    path: '/pickup-request/',
    isApplication: true
  },
  PICKUPHIST: {
    uri: '',
    icon: 'fal fa-dolly-flatbed',
    path: '/pickup-request/',
    isApplication: true
  },
  ONL100: {
    uri: '',
    icon: 'fal fa-chart-bar',
    path: '/online-reporting/',
    isApplication: true
  },
  BOL100: {
    uri: '',
    icon: 'fal fa-file-spreadsheet',
    path: '/bill-of-lading/',
    isApplication: true
  },
  EBG10O120: {
    uri: '',
    icon: 'fal fa-folder',
    path: '/commodity-library/',
    isApplication: true
  },
  DSN10O100: {
    uri: '/resources/density-calculator/',
    icon: 'fal fa-calculator-alt',
    path: '',
    isApplication: true
  },
  LTLRATEQOT: {
    uri: '',
    icon: 'fal fa-percent',
    path: '/rate-quote/',
    isApplication: true
  },
  TIMECRIT: {
    uri: '',
    icon: 'fal fa-weight',
    path: '/rate-quote/',
    isApplication: true
  },
  VTLQUOTE: {
    uri: '',
    icon: 'fal fa-truck-container',
    path: '/rate-quote/',
    isApplication: true
  },
  QUOTEHIST: {
    uri: '',
    icon: 'fal fa-usd-circle',
    path: '/rate-quote/',
    isApplication: true
  },
  WRVIEWING: {
    uri: '',
    icon: 'fal fa-weight',
    path: '/weight-and-research/',
    isApplication: true
  }
};
