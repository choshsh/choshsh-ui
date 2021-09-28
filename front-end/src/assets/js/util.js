// json 객체 hard clone
export function cloneObject(obj) {
  var clone = {};
  for (var key in obj) {
    if (typeof obj[key] == "object" && obj[key] != null) {
      clone[key] = cloneObject(obj[key]);
    } else {
      clone[key] = obj[key];
    }
  }

  return clone;
}

// 금액에 쉼표 추가
export function numberWithCommas(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// yyyyMMdd -> yyyy-MM-dd
export function dateFormat(dateStr) {
  if (dateStr) {
    return (
      dateStr.substring(0, 4) +
      "-" +
      dateStr.substring(4, 6) +
      "-" +
      dateStr.substring(6, 8)
    );
  }
}
