// Keep the page dynamic so the school year and assignments reflect current data.
export const dynamic = "force-dynamic";

import { getSchoolYearStartYear } from "@/lib/dateUtils/WorkdaysCalendarUtils";
import MonthAssignmentsWrapper from "@/components/MonthAssignmentsWrapper/MonthAssignmentsWrapper";
import styles from "./page.module.css";

export default function MonthAssignments() {
  const schoolYearStartYear = getSchoolYearStartYear();
  const schoolYearEndYear = schoolYearStartYear + 1;

  return (
    <>
      <div className={styles.header}>
        <h1>Monatsplanung</h1>
        <p className={styles.subtitle}>
          Im Ausbildungsjahr {schoolYearStartYear}-{schoolYearEndYear}
        </p>
      </div>
      <MonthAssignmentsWrapper schoolYearStartYear={schoolYearStartYear} />
    </>
  );
}
