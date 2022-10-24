import Link from "next/link";
import { Fragment } from "react";

function NewsPage() {
  return (
    <Fragment>
      <h1>This is News page</h1>
      <ul>
        <li><Link href="news/goodToLearn">This is good to learn</Link></li>
        <li><Link href="news/season1">season 1 big huge suprise</Link></li>
      </ul>
    </Fragment>
  );
}

export default NewsPage;
