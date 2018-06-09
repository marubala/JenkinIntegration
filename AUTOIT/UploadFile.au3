UploadFile()

Func UploadFile()

	Sleep(2000)

	Local $sText = WinGetTitle("[ACTIVE]")

	; Wait 10 seconds for the window to appear.
    Local $hwnd = WinWait($sText, "", 10)

	;Activate the window
	WinActivate($hwnd)


    ; Set input focus to the edit control of window using the handle returned by WinWait.
    ControlFocus($hWnd, "", "Edit1")

    ; Wait for 2 seconds.
    Sleep(1000)

	;set the value in the edit text box
	ControlSetText($hWnd, "", "Edit1", @ScriptDir&$CmdLine[1])
	;ControlSetText($hWnd, "", "Edit1", @ScriptDir&"\ImportCSV.csv")

   ; Wait for 2 seconds.
    Sleep(1000)

	;click on the Save button
	ControlClick($hWnd, "", "Button1")

EndFunc