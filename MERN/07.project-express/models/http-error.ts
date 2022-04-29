class HttpError extends Error {
  private readonly _code: number;
  constructor(message: string, errorCode: number) {
    super(message);
    this._code = errorCode;
  }
  get code() {
    return this._code;
  }
}

export default HttpError;
