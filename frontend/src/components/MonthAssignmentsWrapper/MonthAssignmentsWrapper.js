import TeamsAPI from "@/lib/api/Teams";
import ChoreCategoriesAPI from "@/lib/api/ChoreCategories";
import MonthAssignmentsBoard from "../MonthAssignmentsBoard/MonthAssignmentsBoard";

export default async function MonthAssignmentsWrapper({ schoolYearStartYear }) {
  let teams = [];
  let choreCategories = [];

  try {
    teams = await TeamsAPI.readAll();
  } catch (error) {
    console.error("Teams konnten nicht geladen werden.", error);
  }

  try {
    choreCategories = await ChoreCategoriesAPI.readAll();
  } catch (error) {
    console.error("Chore Categories konnten nicht geladen werden.", error);
  }

  return (
    <section>
      <MonthAssignmentsBoard
        schoolYearStartYear={schoolYearStartYear}
        teams={teams}
        choreCategories={choreCategories}
      />
    </section>
  );
}
