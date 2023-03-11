export const navigations = [
  {
    name: 'Trang chủ',
    path: '/dashboard/default',
    icon: 'home',
    role: ['ADMIN', 'LEADER', 'HR', 'ACCOUNTANCY'],
  },
  {
    name: 'Tuyển dụng',
    icon: 'person_add',
    children: [
      { name: 'Lập kế hoạch tuyển dụng', path: '/plan/recruit' },
      { name: 'Tạo hồ sơ ứng viên', path: '/plan/candidate' },
    ],
    role: ['ADMIN', 'HR'],
  },
  {
    name: 'Quản lý danh mục',
    icon: 'category',
    children: [
      { name: 'Danh sách phòng ban', path: '/manage/department' },
      { name: 'Danh sách chức vụ', path: '/manage/position' },
      { name: 'Danh sách bằng cấp', path: '/manage/certificate' },
      { name: 'Danh sách chứng chỉ', path: '/manage/language' },
    ],
    role: ['ADMIN', 'HR'],
  },
  {
    name: 'Phê duyệt',
    icon: 'check_box',
    children: [
      { name: 'Phê duyệt kế hoạch TD', path: '/leader/approval-recruit' },
      { name: 'Danh sách KH đã phê duyệt', path: '/leader/approved' },
      { name: 'Phê duyệt hồ sơ ứng viên', path: '/leader/approval-candidate' },
      { name: 'Phê duyệt hồ sơ nhân viên', path: '/leader/approval-employee' },
      { name: 'QĐ Khen thưởng - Kỷ luật', path: '/leader/commendation-and-discipline' },
      { name: 'QĐ Tăng lương', path: '/leader/approve-promote' },
      { name: 'QĐ Nghỉ việc', path: '/leader/lay-off-or-quit-job' },
    ],
    role: ['ADMIN', 'LEADER'],
  },
  {
    name: 'Quản lý nhân viên',
    icon: 'account_circle',
    children: [
      { name: 'Chấm công', path: '/manage/time-keeping' },
      { name: 'Tạo hồ sơ nhân viên', path: '/manage/create-employee' },
      { name: 'Cập nhật diễn biến', path: '/manage/update-happening' },
      { name: 'QĐ Tăng lương', path: '/manage/promote' },
      { name: 'Báo cáo thống kê', path: '/manage/statistic-report' },
    ],
    role: ['ADMIN', 'HR'],
  },
  {
    name: 'Quản lý lương',
    icon: 'paid',
    children: [
      { name: 'Tính lương', path: '/payment-salary' },
      { name: 'QĐ Khen thưởng - Kỷ luật', path: '/commendation-and-discipline' },
      { name: 'Báo cáo thống kê', path: '/session/signin' },
    ],
    role: ['ADMIN', 'ACCOUNTANCY'],
  },
  {
    name: 'Quản lí hợp đồng',
    path: '/manage/contract',
    icon: 'history_edu',
    role: ['ADMIN', 'HR'],
  },
  {
    name: 'Quản lý người dùng',
    path: '/manage/user',
    icon: 'person',
    role: ['ADMIN', 'LEADER'],
  },
];
