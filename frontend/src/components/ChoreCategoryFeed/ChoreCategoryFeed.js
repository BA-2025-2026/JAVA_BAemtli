"use client";

import { useState } from "react";
import ChoreCategoryCard from "../ChoreCategoryCard/ChoreCategoryCard";
import ChoreCategoryForm from "../ChoreCategoryForm/ChoreCategoryForm";
import styles from "./ChoreCategoryFeed.module.css";

export default function ChoreCategoryFeed({ choreCategories }) {
  const [isAdding, setIsAdding] = useState(false);

  return (
    <>
      <div className={styles.pageActions}>
        {!isAdding ? (
          <button
            className={styles.addButton}
            type="button"
            onClick={() => setIsAdding(true)}
          >
            <span className="material-symbols-outlined">add</span>
            <span>Neue Ämtlikategorie</span>
          </button>
        ) : (
          <div className={styles.formCard}>
            <ChoreCategoryForm
              onSuccess={() => setIsAdding(false)}
              onCancel={() => setIsAdding(false)}
            />
          </div>
        )}
      </div>
      <div>
        {choreCategories.map((choreCategory) => (
          <ChoreCategoryCard
            key={choreCategory.id}
            choreCategory={choreCategory}
          />
        ))}
      </div>
      {choreCategories.length === 0 && (
        <p className="noEntityInfo">Noch keine Ämtlikategorie vorhanden.</p>
      )}
    </>
  );
}
