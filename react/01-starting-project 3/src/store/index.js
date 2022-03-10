// const redux = require('redux');
import { createStore } from 'redux';
import { createSlice, configureStore } from '@reduxjs/toolkit'


const counterInitialState = {
    counter: 0,
    showCounter: true
};

const counterSlice = createSlice({
    name: 'counter',
    initialState: counterInitialState,
    reducers: {
        increment(state) {
            state.counter++;
        },
        decrement(state) {
            state.counter--;
        },
        increase(state, action) {
            state.counter += action.payload
        },
        toggle(state) { 
            state.showCounter = !state.showCounter
        }
    }
});

export const counterActions = counterSlice.actions;

const authInitialState = {
    isAuthentiaction: false
}

const authSlice = createSlice({
    name: 'authentication',
    initialState: authInitialState,
    reducers: {
        login(state) {
            state.isAuthentiaction = true;
        },
        logout(state) {
            state.isAuthentiaction = false;
        }
    }
});

export const authActions = authSlice.actions;

export const store = configureStore({
    reducer: {
        counter: counterSlice.reducer,
        auth: authSlice.reducer
    }
});



