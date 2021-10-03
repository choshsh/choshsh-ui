const USER = "/api/user";
const ADMIN = "/api/admin";
const VM = "/api/vm";
const Azure = "/api/azure";
const CODE = "/api/code";
const SERVER = "/interface/server";
const NETWORKER = "/interface/networker";
const EGRESSION = "/interface/egression";
const CALENDAR = "/api/calendar";
const LICENSE = "/api/license";
const SCRIPT = "/api/script";

export default {
  server: {
    list: SERVER,
    info: SERVER,
    serverChange: SERVER + "/serverChange",
    chartThisMonth: SERVER + "/chartThisMonth",
    excel: SERVER + "/excel",
    issue: SERVER + "/issue",
    issueSummary: SERVER + "/issue/summary",
    issueSummaryService: SERVER + "/issue/summary/service",
    issueCountDay: SERVER + "/issue/count/day",
    findByInc: SERVER + "/icnNo",
  },
  calendar: {
    checkIdc: CALENDAR + "/checkIdc",
  },
  auth: {
    login: USER + "/login",
  },
  admin: {
    header: ADMIN + "/header",
    iframe: ADMIN + "/iframe",
    env: ADMIN + "/env",
  },
  vm: {
    list: VM,
    info: VM,
    update: VM,
    delete: VM,
    excel: VM + "/excel",
  },
  azure: {
    list: Azure,
    info: Azure,
    update: Azure,
    excel: Azure + "/excel",
    cost: Azure + "/cost",
  },
  license: {
    list: LICENSE,
    info: LICENSE,
    update: LICENSE,
    category: LICENSE + "/category",
    use: LICENSE + "/use",
  },
  networker: {
    list: NETWORKER + "/backups",
    listLast24: NETWORKER + "/backupsLast24",
    saveset: NETWORKER + "/getSaveSetList",
    excel: NETWORKER + "/excel",
    code: NETWORKER + "/code",
  },
  egression: {
    list: EGRESSION,
    chart: EGRESSION + "/chart",
    excel: EGRESSION + "/excel",
  },
  code: {
    location: CODE + "/location",
    vmHost: CODE + "/vmHost",
    vmOs: CODE + "/vmOs",
    vmPower: CODE + "/vmPower",
    vmUsage: CODE + "/vmUsage",
    yn: CODE + "/yn",
    serverEnv: CODE + "/serverEnv",
    azureLocation: CODE + "/azureLocation",
    scriptCategory: CODE + "/scriptCategory",
    scriptCode: CODE + "/scriptCode",
  },
  script: {
    list: SCRIPT,
    info: SCRIPT,
    update: SCRIPT,
    sourcecode: SCRIPT + "/sourcecode",
  },
};
