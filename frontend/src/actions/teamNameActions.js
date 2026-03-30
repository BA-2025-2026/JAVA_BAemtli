"use server";

const handleUpdate = async (id, name) => {
  const { data, error } = await api.patch(`/teams/${id}`, { name });
  if (error) return extractError(error);
  setTeams(teams.map((t) => (t.id === id ? data : t)));
  setEditingId(null);
  return null;
};

export async function teamNameActions(prevState, formData) {
  let message = "";
  const trimmed = formData.get("name").trim();

  if (!trimmed) {
    message = "Name darf nicht leer sein";
  } else if (trimmed.length < 2 || trimmed.length > 30) {
    message = "Name muss zwischen 2 und 30 Zeichen lang sein";
  } else {
    await handleUpdate();
  }
}
