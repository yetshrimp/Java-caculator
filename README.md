# 类windows的java计算器

使用java图形化开发实现了科学计算器的部分功能

1.本计算器仿照windows计算器的外观及科学计算器、标准计算器面板功能而实现。

2.本计算器实现了windows计算器的科学计算器、标准计算器的所有运算函数，并增加了度
  分秒的换算功能。

3.本计算器实时接受用户输入、计算结果并在显示框更新表达式和结果的显示

4.错误处理：
  
  算数错误：给出提示并禁止下一步输入，只能执行清空操作。
  例子：log(0)、3/0、arcsin(10)……
  
  可补全的输入错误：自动以0补全
  例子： (+) 转变为 (0+0) ……
  
  可更新的输入错误：清除上一步输入，以当前输入为准
  例子：连续 +-/ 运算最后保留 / 运算

  可拒绝的输入错误：不做任何操作
  例子：4.65.视为4.65，不匹配 ( 的 ) 不接受

5.显示处理：清除浮点数多余的小数点和0，以及对因精度导致的结果误差截断处理
  例子:4.567000只输出4.567……

6.遗憾：历史面板中动态记录历史的组件未能如愿显示。

######################################################################

# windows like calculator programed by java

a program coded by java to achieve part of function of scientific calculator

1. This calculator is modeled after the appearance of the windows calculator and the scientific calculator and standard  
  calculator panel functions.

2. This calculator implements all the arithmetic functions of the scientific calculator and standard calculator of the windows
  calculator, and increases the degree.
  Counting and counting conversion function.

3. The calculator accepts user input, calculates the result in real time, and updates the display of expressions and results in
  the display box.

4. Error handling:
  
  Arithmetic error: Give a prompt and disable the next input, only the empty operation.
  Examples: log(0), 3/0, arcsin(10)...
  
  Completion input error: automatically complete with 0
  Example: (+) is converted to (0+0) ......
  
  Updatable input error: Clear previous input, subject to current input
  Example: continuous +-/ operation last reserved / operation

  Rejectable input error: do nothing
  Example: 4.65. Treated as 4.65, does not match ( ) does not accept

5. Display processing: clear the extra decimal point and 0 of the floating point number, and the error truncation of the result
  caused by the precision
  Example: 4.567000 only outputs 4.567...

6. Unfortunately: the components of the history history record in the history panel are not displayed as expected.
