import { getJSON, postJSON, putJSON, deleteJSON, BASE_URL } from ".";

const URL = `${BASE_URL}/teams`;

const TeamsAPI = {
  readAll(accessToken = null) {
    return getJSON(URL, accessToken); //
  },
  read(id, accessToken) {
    return getJSON(`${URL}/${id}`, accessToken); //
  },
  create(activity, accessToken) {
    return postJSON(URL, activity, accessToken);
  },
  update(id, activity, accessToken) {
    return putJSON(`${URL}/${id}`, activity, accessToken);
  },
  delete(id, accessToken) {
    return deleteJSON(`${URL}/${id}`, accessToken);
  },
};

export default TeamsAPI;
