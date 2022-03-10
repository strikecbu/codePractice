import { useEffect, useState } from "react";

let globalState = {};
let listeners = [];
let actions = {};

const useStore = (isListen = true) => {
  const setState = useState(globalState)[1];

  useEffect(() => {
    if (isListen) {
      listeners.push(setState);
    }

    return () => {
      if (isListen) {
        listeners = listeners.filter((listener) => listener !== setState);
      }
    };
  }, [setState, isListen]);

  const dispatch = (action, payload) => {
    const newState = actions[action](globalState, payload);
    globalState = { ...globalState, ...newState };
    for (let listener of listeners) {
      listener(globalState);
    }
  };

  return [globalState, dispatch];
};

export default useStore;

export const initialState = (initState, initActions) => {
  if (initState) {
    globalState = { ...globalState, ...initState };
  }
  actions = { ...actions, ...initActions };
};
