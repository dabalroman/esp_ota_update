export default class DateUtility {
  static formatDateString (dateAsString) {
    let d = new Date(dateAsString);
    return (
      d.getHours().toString().padStart(2, '0') + ':'
      + d.getMinutes().toString().padStart(2, '0') + ':'
      + d.getSeconds().toString().padStart(2, '0') + ' '
      + d.getDate().toString().padStart(2, '0') + '/'
      + (d.getMonth() + 1).toString().padStart(2, '0') + '/'
      + d.getFullYear()
    );
  }
}
