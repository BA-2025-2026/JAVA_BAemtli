import ChoreCategoriesAPI from "@/lib/api/ChoreCategories";
import ChoreCategoryFeed from "@/components/ChoreCategoryFeed/ChoreCategoryFeed";

export default async function ChoreCategoryWrapper() {
  // Fetch Chore Categories
  let choreCategories = [];

  try {
    choreCategories = await ChoreCategoriesAPI.readAll();
  } catch (error) {
    console.error("Ämtlikategorien konnten nicht geladen werden.", error);
  }

  return (
    <section>
      <ChoreCategoryFeed choreCategories={choreCategories} />
    </section>
  );
}
