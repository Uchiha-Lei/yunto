import { generateService } from '@umijs/openapi'

generateService({
  requestLibPath: "import request from '@/request.ts'", // ‘请求’引入代码
  schemaPath: 'http://localhost:8123/api/v2/api-docs', // 参考哪个文档生成
  serversPath: './src', // 生成的代码放在哪个目录下
})
