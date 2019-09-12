export class RequestAdditionalInfoRequest {

    bolSelected: boolean;
    description: string;
    drSelected: boolean;
    emailAddress: string;
    faxNumber: string;
    name: string;
    phoneNumber: string;
    phoneNumberExt: string;
    proNumber: string;
    problem: string;
    wrSelected; boolean;

    constructor(userInfo, proNumber) {
        this.bolSelected = false;
        this.description = "";
        this.drSelected = false;
        this.emailAddress = userInfo.emailAddress ? userInfo.emailAddress : "";
        this.faxNumber = "";
        this.name = userInfo.name ? userInfo.name : "";
        this.phoneNumber = userInfo.phoneNumber ? userInfo.phoneNumber : "";
        this.phoneNumberExt = "";
        this.proNumber = (proNumber !== '') ? proNumber : "";
        this.problem = "";
        this.wrSelected = false;
    }
}