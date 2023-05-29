function getBackURL() {
    if (process.env.REACT_APP_ENV === "DEV")
      return process.env.REACT_APP_BACK_URL_DEV;
    if (process.env.REACT_APP_ENV === "PRD")
      return process.env.REACT_APP_BACK_URL_PRD;
    return process.env.REACT_APP_BACK_URL_LOCAL;
  }
  
  export const BACK_URL = getBackURL();