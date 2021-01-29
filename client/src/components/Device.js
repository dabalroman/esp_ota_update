export default class Device {
  static statusMap = ['New', 'Up to date', 'Needs update', 'No software'];

  static mapStatusToString (status) {
    return Device.statusMap[status];
  }
}
