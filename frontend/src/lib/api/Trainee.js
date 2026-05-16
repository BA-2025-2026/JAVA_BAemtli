import { getJSON, postJSON, patchJSON, deleteJSON, BASE_URL } from ".";

const URL = `${BASE_URL}/trainees`;

const TraineesAPI = {
  readAll(accessToken = null) {
    return getJSON(URL, accessToken); //
  },
  read(id, accessToken) {
    return getJSON(`${URL}/${id}`, accessToken); //
  },
  create(trainee, accessToken) {
    return postJSON(URL, trainee, accessToken);
  },
  update(id, trainee, accessToken) {
    return patchJSON(`${URL}/${id}`, trainee, accessToken);
  },
  delete(id, accessToken) {
    return deleteJSON(`${URL}/${id}`, accessToken);
  },
};

export default TraineesAPI;
