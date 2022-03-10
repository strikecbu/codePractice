import React from "react";
// import { useDispatch } from 'react-redux';

import Card from "../UI/Card";
import "./ProductItem.css";
// import { toggleFav } from '../../store/actions/products';
import useStore from "../../usehook-store/store";

const ProductItem = React.memo((props) => {
  const dispatch = useStore(false)[1];
  // const dispatch = useDispatch();

  const toggleFavHandler = () => {
    // dispatch(toggleFav(props.id));
    dispatch("TOGGLE_FAV", props.id);
  };
  console.log("RENDING");
  return (
    <Card style={{ marginBottom: "1rem" }}>
      <div className="product-item">
        <h2 className={props.isFav ? "is-fav" : ""}>{props.title}</h2>
        <p>{props.description}</p>
        <button
          className={!props.isFav ? "button-outline" : ""}
          onClick={toggleFavHandler}
        >
          {props.isFav ? "Un-Favorite" : "Favorite"}
        </button>
      </div>
    </Card>
  );
});

export default ProductItem;
