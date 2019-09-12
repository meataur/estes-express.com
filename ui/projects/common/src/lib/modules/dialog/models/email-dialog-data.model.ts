export abstract class EmailDialogData {
  abstract title: string;
  abstract message: string;
  abstract url: string;
  abstract emailKey?: string;
  abstract formatKey?: string;
  abstract formats?: any;
  abstract requestPayload: any;
  abstract successMessage?: string;
}
