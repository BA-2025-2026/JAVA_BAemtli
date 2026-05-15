import { getJSON, postJSON, patchJSON, deleteJSON, BASE_URL } from ".";

const URL = `${BASE_URL}/teams`;

const TeamsAPI = {
  readAll(accessToken = null) {
    return getJSON(URL, accessToken); //
  },
  read(id, accessToken) {
    return getJSON(`${URL}/${id}`, accessToken); //
  },
  create(team, accessToken) {
    return postJSON(URL, team, accessToken);
  },
  update(id, team, accessToken) {
    return patchJSON(`${URL}/${id}`, team, accessToken);
  },
  delete(id, accessToken) {
    return deleteJSON(`${URL}/${id}`, accessToken);
  },
};

export default TeamsAPI;
