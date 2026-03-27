"use client";
import styles from "./Sidebar.module.css";
import Link from "next/link";
import { usePathname } from "next/navigation";

export default function Sidebar() {
  const pathname = usePathname();

  return (
    <nav className={styles.sidebar}>
      <div>
        <h3>Ämtliplantool</h3>
        <p>ICT-Campus</p>
      </div>

      <div className={styles.navGroup}>
        <ul>
          <li>
            <Link
              href="/"
              className={`${styles.navigationItem} ${pathname === "/" ? styles.active : ""}`}
            >
              <span className="material-symbols-outlined">home</span>
              Home
            </Link>
          </li>
          <li>
            <Link
              href="/dayassignments"
              className={`${styles.navigationItem} ${pathname === "/dayassignments" ? styles.active : ""}`}
            >
              <span className="material-symbols-outlined">calendar_month</span>
              <p>Einteilungen</p>
            </Link>
          </li>
        </ul>
      </div>

      <div className={styles.navGroup}>
        <h4>Admin-Bereich</h4>
        <ul>
          <li>
            <Link
              href="/chorecategories"
              className={`${styles.navigationItem} ${pathname === "/chorecategories" ? styles.active : ""}`}
            >
              <span className="material-symbols-outlined">
                cleaning_services
              </span>
              <p>Ämtlikatalog</p>
            </Link>
          </li>
          <li>
            <Link
              href="/teams"
              className={`${styles.navigationItem} ${pathname === "/teams" ? styles.active : ""}`}
            >
              <span className="material-symbols-outlined">group</span>
              <p>Teams & Lernende</p>
            </Link>
          </li>
          <li>
            <Link
              href="/monthassignments"
              className={`${styles.navigationItem} ${pathname === "/monthassignments" ? styles.active : ""}`}
            >
              <span className="material-symbols-outlined">date_range</span>
              <p>Monatseinteilung</p>
            </Link>
          </li>
          <li>
            <Link
              href="/workdays"
              className={`${styles.navigationItem} ${pathname === "/workdays" ? styles.active : ""}`}
            >
              <span className="material-symbols-outlined">work</span>
              <p>Arbeitstage</p>
            </Link>
          </li>
        </ul>
      </div>

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
