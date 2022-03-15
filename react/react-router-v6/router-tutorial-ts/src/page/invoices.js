import { Link, Outlet } from "react-router-dom";
import { getInvoices } from "../data";

const Invoices = () => {
  const invoices = getInvoices();

  return (
    <main style={{ padding: "1rem 0" }}>
      <h2>Invoices</h2>
      <nav>
          {invoices.map((item) => {
            return (
              <p key={item.number}>
                <Link to={`${item.number}`} >
                  {item.name}
                </Link>
              </p>
            );
          })}
      </nav>
      <div>
          <Outlet />
      </div>
    </main>
  );
};
export default Invoices;
