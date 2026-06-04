// Keep the page dynamic so the school year is always derived from the current date.
export const dynamic = "force-dynamic";

import SchoolYearCalendar from "@/components/SchoolYearCalendar/SchoolYearCalendar";
import { getSchoolYearStartYear } from "@/lib/dateUtils/workdaysCalendar";
import styles from "./page.module.css";

export default function Workdays() {
  const schoolYearStartYear = getSchoolYearStartYear();
  const schoolYearEndYear = schoolYearStartYear + 1;

  return (
    <>
      <div className={styles.header}>
        <h1>Arbeitstage</h1>
        <p className={styles.subtitle}>
          Schuljahr {schoolYearStartYear} bis {schoolYearEndYear}
        </p>
      </div>
      <SchoolYearCalendar schoolYearStartYear={schoolYearStartYear} />
    </>
  );
}
