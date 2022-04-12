import { getBlob, getStorage, ref } from 'firebase/storage';
import { FC, useContext, useEffect, useState } from 'react';
import { AppContext } from '../store/AppContext';

const Download: FC<{ word: string }> = ({ word, children }) => {
  const ctx = useContext(AppContext);
  const [url, setUrl] = useState<string>();
  useEffect(() => {
    async function getUrl() {
      // Get a reference to the storage service, which is used to create references in your storage bucket
      const storage = getStorage(ctx.firebaseApp);
      const zipRef = ref(storage, '/unzip.zip');
      const blob = await getBlob(zipRef);

      //需要設定storage cros:
      // 1. gcloud auth login
      // 2. gsutil cors set cors.json gs://BUCKET_NAME
      setUrl(URL.createObjectURL(blob));
    }
    if (ctx.firebaseApp) {
      getUrl();
    }
  }, [ctx.firebaseApp]);

  return (
    <div>
      <p>{word}</p>
      <a download="iisi_unzip.zip" href={url}>
        Download
      </a>
    </div>
  );
};

export default Download;
