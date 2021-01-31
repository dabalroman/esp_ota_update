import DateUtility from '@/utils/DateUtility';

export default class DeviceHistory {
  statusMap = ['Updating...', 'Success', 'Error'];
  statusClassMap = ['text-warning', 'text-success', 'text-danger'];

  constructor (updateId, deviceId, timestamp, status,
    baseVersion, baseMd5, baseFile, baseCreatedAt,
    toVersion, toMd5, toFile, toCreatedAt) {
    this._updateId = updateId;
    this._deviceId = deviceId;
    this._timestamp = timestamp;
    this._status = status;
    this._baseVersion = baseVersion;
    this._baseMd5 = baseMd5;
    this._baseFile = baseFile;
    this._baseCreatedAt = baseCreatedAt;
    this._toVersion = toVersion;
    this._toMd5 = toMd5;
    this._toFile = toFile;
    this._toCreatedAt = toCreatedAt;
  }

  get updateId () {
    return this._updateId;
  }

  get deviceId () {
    return this._deviceId;
  }

  get timestamp () {
    return DateUtility.formatDateString(this._timestamp);
  }

  get status () {
    return this.statusMap[this._status];
  }

  get statusClass () {
    return this.statusClassMap[this._status];
  }

  get baseVersion () {
    return this._baseVersion ?? 'Unknown';
  }

  get baseMd5 () {
    return this._baseMd5;
  }

  get baseFile () {
    return this._baseFile;
  }

  get baseCreatedAt () {
    return DateUtility.formatDateString(this._baseCreatedAt);
  }

  get toVersion () {
    return this._toVersion ?? 'Unknown';
  }

  get toMd5 () {
    return this._toMd5;
  }

  get toFile () {
    return this._toFile;
  }

  get toCreatedAt () {
    return DateUtility.formatDateString(this._toCreatedAt);
  }
}
