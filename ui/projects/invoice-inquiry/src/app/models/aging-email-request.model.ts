export class AgingEmailRequest {
  bucket: string; //Aging bucket : 1-8; 0 when PROs selected
  emailAddresses: string[]; //List of e-mail addresses to receive A/R aging information\
  fileFormat: string; //File format - xls/csv/txt
  pros: string[]; //List of PROs for which to receive A/R aging information XXX-XXXXXXX
}