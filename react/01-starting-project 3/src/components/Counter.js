import { Component } from 'react';
import { counterActions } from '../store';
import { useSelector, useDispatch, connect } from 'react-redux';
import classes from './Counter.module.css';

const Counter = () => {
  console.log('init');

  const counter = useSelector(state => state.counter.counter);
  const showCounter = useSelector(state => state.counter.showCounter);
  const dispatch = useDispatch();

  const toggleCounterHandler = () => {
    dispatch(counterActions.toggle())
  };

  const incrementHandler = () => {
    dispatch(counterActions.increment());
  }
  const decrementHandler = () => {
    dispatch(counterActions.decrement());
  }

  const increaseHandler = () => {
    dispatch(counterActions.increase(10));
  }



  return (
    <main className={classes.counter}>
      <h1>Redux Counter</h1>
      {/* <div className={classes.value}>-- COUNTER VALUE --</div> */}
      {showCounter && <div className={classes.value}>{counter}</div>}
      <div>
        <button onClick={incrementHandler}>increment</button>
        <button onClick={increaseHandler}>increase by 10</button>
        <button onClick={decrementHandler}>decrement</button>
      </div>
      <button onClick={toggleCounterHandler}>Toggle Counter</button>
    </main>
  );
};

export default Counter;


// class Counter extends Component {

//   constructor() {
//     super();
//     this.incrementHandler = this.incrementHandler.bind(this);
//     this.decrementHandler = this.decrementHandler.bind(this);
//   }

//   incrementHandler = () => {
//     this.props.increment();
//   }

//   decrementHandler = () => {
//     this.props.decrement();
//   }

//   toggleCounterHandler() {

//   }

//   render() {
//     return (
//       <main className={classes.counter}>
//         <h1>Redux Counter</h1>
//         {/* <div className={classes.value}>-- COUNTER VALUE --</div> */}
//         <div className={classes.value}>{this.props.counter}</div>
//         <div>
//           <button onClick={this.incrementHandler}>increment</button>
//           <button onClick={this.decrementHandler}>decrement</button>
//         </div>
//         <button onClick={this.toggleCounterHandler}>Toggle Counter</button>
//       </main>
//     )
//   }

// }

// const mapStateToProps = (state) => {
//   return {
//     counter: state.counter
//   }
// }

// const mapDispatchToProps = (dispatch) => {
//   return {
//     increment: () => dispatch({type: actionType.increment}),
//     decrement: () => dispatch({type: actionType.decrement})
//   }
// }

// export default connect(mapStateToProps, mapDispatchToProps)(Counter);