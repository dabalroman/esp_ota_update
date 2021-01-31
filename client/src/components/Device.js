import DateUtility from '@/utils/DateUtility';

export default class Device {
  statusMap = ['New', 'Up to date', 'Needs update', 'No software', 'Update in progress'];
  statusClassMap = ['text-primary', 'text-success', 'text-warning', 'text-danger', 'text-warning'];

  constructor (id, name, mac, softwareNameScheme, softwareVersion, status, lastSoftwareCheck, lastSoftwareUpdate) {
    this._id = id;
    this._name = name;
    this._mac = mac;
    this._softwareNameScheme = softwareNameScheme;
    this._status = status;
    this._softwareVersion = softwareVersion;
    this._lastSoftwareCheck = lastSoftwareCheck;
    this._lastSoftwareUpdate = lastSoftwareUpdate;
  }

  get id () {
    return this._id;
  }

  get name () {
    return this._name;
  }

  get mac () {
    return this._mac;
  }

  get softwareNameScheme () {
    return this._softwareNameScheme;
  }

  get status () {
    return this.statusMap[this._status];
  }

  get statusClass () {
    return this.statusClassMap[this._status];
  }

  get softwareVersion () {
    return this._softwareVersion ?? 'Unknown';
  }

  get lastSoftwareCheck () {
    return DateUtility.formatDateString(this._lastSoftwareCheck);
  }

  get lastSoftwareUpdate () {
    return DateUtility.formatDateString(this._lastSoftwareUpdate);
  }
}
