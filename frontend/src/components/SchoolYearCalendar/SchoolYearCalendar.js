import styles from "./SchoolYearCalendar.module.css";
import WorkdaysAPI from "@/lib/api/Workdays";
import {
  createWorkdayDateSet,
  getSchoolYearMonths,
} from "@/lib/dateUtils/WorkdaysCalendarUtils";
import WorkdayMonth from "../WorkdayMonth/WorkdayMonth";

export default async function SchoolYearCalendar({ schoolYearStartYear }) {
  let workdays = [];

  try {
    workdays = await WorkdaysAPI.readAll();
  } catch (error) {
    console.error("Workdays konnten nicht geladen werden.", error);
  }

  const months = getSchoolYearMonths(schoolYearStartYear);
  const workdayDateSet = createWorkdayDateSet(workdays ?? []);

  return (
    <div className={styles.calendar}>
      {months.map(({ year, monthIndex, monthName }) => (
        <WorkdayMonth
          key={`${year}-${monthIndex}`}
          year={year}
          monthIndex={monthIndex}
          monthName={monthName}
          workdayDateSet={workdayDateSet}
        />
      ))}
    </div>
  );
}
