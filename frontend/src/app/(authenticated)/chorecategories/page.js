// Prevent Container Image build from trying to pre-render this route statically without access to data
export const dynamic = "force-dynamic";

import ChoreCategoryWrapper from "@/components/ChoreCategoryWrapper/ChoreCategoryWrapper";

export default function ChoreCategories() {
  return (
    <>
      <h1>Ämtlikategorien</h1>
      <ChoreCategoryWrapper />
    </>
  );
}
