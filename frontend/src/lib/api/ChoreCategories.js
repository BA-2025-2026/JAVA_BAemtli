import { getJSON, postJSON, patchJSON, deleteJSON, BASE_URL } from ".";

const URL = `${BASE_URL}/chorecategories`;

const ChoreCategoriesAPI = {
  readAll(accessToken = null) {
    return getJSON(URL, accessToken);
  },
  read(id, accessToken) {
    return getJSON(`${URL}/${id}`, accessToken);
  },
  create(choreCategory, accessToken) {
    return postJSON(URL, choreCategory, accessToken);
  },
  update(id, choreCategory, accessToken) {
    return patchJSON(`${URL}/${id}`, choreCategory, accessToken);
  },
  delete(id, accessToken) {
    return deleteJSON(`${URL}/${id}`, accessToken);
  },
};

export default ChoreCategoriesAPI;
