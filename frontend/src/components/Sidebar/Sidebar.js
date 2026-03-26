import styles from "./Sidebar.module.css";
import Link from "next/link";

export default function Sidebar() {
  return (
    <nav className={styles.sidebar}>
      <div>
        <h3>Ämtliplantool</h3>
        <p>ICT-Campus</p>
      </div>

      <ul>
        <li>
          <Link href="/" className={styles.navigationItem}>
            <span className="material-symbols-outlined">home</span>
            Home
          </Link>
        </li>
        <li>
          <Link href="/dayassignments" className={styles.navigationItem}>
            <span className="material-symbols-outlined">calendar_month</span>
            <p>Einteilungen</p>
          </Link>
        </li>
        <h4>Admin-Bereich</h4>
        <li>
          <Link href="/dayassignments" className={styles.navigationItem}>
            <span className="material-symbols-outlined">cleaning_services</span>
            <p>Ämtlikatalog</p>
          </Link>
        </li>
        <li>
          <Link href="/dayassignments" className={styles.navigationItem}>
            <span className="material-symbols-outlined">group</span>
            <p>Teams & Lernende</p>
          </Link>
        </li>
        <li>
          <Link href="/dayassignments" className={styles.navigationItem}>
            <span className="material-symbols-outlined">date_range</span>
            <p>Monatseinteilung</p>
          </Link>
        </li>
        <li>
          <Link href="/dayassignments" className={styles.navigationItem}>
            <span className="material-symbols-outlined">work</span>
            <p>Arbeitstage</p>
          </Link>
        </li>
      </ul>

      <div className={styles.logoutArea}>
        <div>
          <p>Eingeloggt als</p>
          <h4>Coach</h4>
        </div>
        <span className="material-symbols-outlined">logout</span>
      </div>
    </nav>
  );
}

// EHER SO UMSETZEN: GRUPPIERUNG!!
<nav className={styles.sidebar}>
  <div className={styles.header}>
    <h3>Ämtliplantool</h3>
    <p>ICT-Campus</p>
  </div>

  <div className={styles.navGroup}>
    <ul>
      <li>
        <Link href="/">Home</Link>
      </li>
      <li>
        <Link href="/dayassignments">Einteilungen</Link>
      </li>
    </ul>
  </div>

  <div className={styles.navGroup}>
    <h4 className={styles.groupTitle}>Admin-Bereich</h4>
    <ul>
      <li>
        <Link href="/duties">Ämtlikatalog</Link>
      </li>
      <li>
        <Link href="/teams">Teams & Lernende</Link>
      </li>
      <li>
        <Link href="/monthly">Monatseinteilung</Link>
      </li>
      <li>
        <Link href="/workdays">Arbeitstage</Link>
      </li>
    </ul>
  </div>

  <div className={styles.logoutArea}>...</div>
</nav>;
