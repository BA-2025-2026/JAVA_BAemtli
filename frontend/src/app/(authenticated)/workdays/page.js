// Keep the page dynamic so the school year is always derived from the current date.
export const dynamic = "force-dynamic";

import SchoolYearCalendar from "@/components/SchoolYearCalendar/SchoolYearCalendar";
import WorkdayInitButton from "@/components/WorkdayInitButton/WorkdayInitButton";
import { getSchoolYearStartYear } from "@/lib/dateUtils/WorkdaysCalendarUtils";
import styles from "./page.module.css";

export default function Workdays() {
  const schoolYearStartYear = getSchoolYearStartYear();
  const schoolYearEndYear = schoolYearStartYear + 1;

  return (
    <>
      <div className={styles.pageTopRow}>
        <div className={styles.header}>
          <h1>Arbeitstage</h1>
          <p className={styles.subtitle}>
            Im Ausbildungsjahr {schoolYearStartYear}-{schoolYearEndYear}
          </p>
        </div>
        <div className={styles.pageActions}>
          <WorkdayInitButton />
        </div>
      </div>
      <SchoolYearCalendar schoolYearStartYear={schoolYearStartYear} />
    </>
  );
}
