import React, { Fragment, useContext, useState } from 'react';

import Card from '../../shared/components/UIElements/Card';
import Button from '../../shared/components/FormElements/Button';
import Modal from '../../shared/components/UIElements/Modal';
import Map from '../../shared/components/UIElements/Map';
import './PlaceItem.css';
import { AuthContext } from '../../shared/contexts/auth-context';

const PlaceItem = (props) => {
  const [showMap, setShowMap] = useState(false);
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const authCtx = useContext(AuthContext);

  const openMapHandler = () => setShowMap(true);

  const closeMapHandler = () => setShowMap(false);

  const clickDeleteHandler = () => {
    setShowDeleteConfirm(true);
  };

  const cancelDeleteHandler = () => {
    setShowDeleteConfirm(false);
  };

  const confirmDeleteHandler = () => {
    setShowDeleteConfirm(false);
    console.log('Deleting...');
  };

  return (
    <React.Fragment>
      <Modal
        show={showMap}
        onCancel={closeMapHandler}
        header={props.address}
        contentClass="place-item__modal-content"
        footerClass="place-item__modal-actions"
        footer={<Button onClick={closeMapHandler}>CLOSE</Button>}
      >
        <div className="map-container">
          <Map center={props.coordinates} zoom={16} />
        </div>
      </Modal>
      <Modal
        show={showDeleteConfirm}
        onCancel={cancelDeleteHandler}
        header="Delete Confirm"
        contentClass="place-item__modal-content__delete"
        footerClass="place-item__modal-actions"
        footer={
          <Fragment>
            <Button inverse onClick={cancelDeleteHandler}>
              Cancel
            </Button>
            <Button danger onClick={confirmDeleteHandler}>
              Confirm
            </Button>
          </Fragment>
        }
      >
        <div className="place-item__">
          <p>
            Are you sure delete this place? It can NOT be redo afterthought!
          </p>
        </div>
      </Modal>
      <li className="place-item">
        <Card className="place-item__content">
          <div className="place-item__image">
            <img src={props.image} alt={props.title} />
          </div>
          <div className="place-item__info">
            <h2>{props.title}</h2>
            <h3>{props.address}</h3>
            <p>{props.description}</p>
          </div>
          <div className="place-item__actions">
            <Button inverse onClick={openMapHandler}>
              VIEW ON MAP
            </Button>
            {authCtx.isLogin && (
              <Fragment>
                <Button to={`/places/${props.id}`}>EDIT</Button>
                <Button danger onClick={clickDeleteHandler}>
                  DELETE
                </Button>
              </Fragment>
            )}
          </div>
        </Card>
      </li>
    </React.Fragment>
  );
};

export default PlaceItem;
