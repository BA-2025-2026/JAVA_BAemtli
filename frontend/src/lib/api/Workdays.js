import { getJSON, postJSON, BASE_URL } from ".";

const URL = `${BASE_URL}/workdays`;

const WorkdaysAPI = {
  readAll(accessToken = null) {
    return getJSON(URL, accessToken);
  },
  initializeCurrentSchoolYear(accessToken = null) {
    return postJSON(`${URL}/initialize-current-school-year`, {}, accessToken);
  },
  toggle(date, accessToken = null) {
    return postJSON(`${URL}/${date}/toggle`, {}, accessToken);
  },
};

export default WorkdaysAPI;
