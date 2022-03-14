export class User {
  constructor(
    public email: string,
    public id: string,
    private _token: string,
    private _expiredTime: Date
  ) {}

  get token() {
    if (new Date > this._expiredTime) {
      return null;
    }
    return this._token;
  }
}
