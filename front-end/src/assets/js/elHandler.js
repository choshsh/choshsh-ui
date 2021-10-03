export function add(array, event) {
  if (event) event.preventDefault();
  var keys = Object.keys(array[array.length - 1]);
  var newItem = new Object();

  keys.forEach((el) => {
    newItem[el] = "";
  });

  array.push(newItem);
}

export function remove(array, index, event) {
  if (event) event.preventDefault();
  array.splice(index, 1);
}