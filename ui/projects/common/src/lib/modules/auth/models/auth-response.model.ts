export class AuthResponse {
  access_token: string;
  token_type: string;
  expires_in: number;
  scope: string;
  accountCode: string;
  session: string;
  accountType: string;
  hash: string;
  auth: [
    {
      authority: string;
    }
  ];
  username: string;

  constructor() {}
}
