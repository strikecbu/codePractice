import { useParams } from "react-router-dom";
import { getInvoice } from "../data";

const Invoice = () => {
  const params = useParams();
  const invoice = getInvoice(params.invoiceId);
  return (
    <main style={{ padding: "1rem 0" }}>
      <h2>{invoice.name}</h2>
        <p>number: {invoice.number}</p>
        <p>due: {invoice.due}</p>
        <p>amount: {invoice.amount}</p>
    </main>
  );
};
export default Invoice;
